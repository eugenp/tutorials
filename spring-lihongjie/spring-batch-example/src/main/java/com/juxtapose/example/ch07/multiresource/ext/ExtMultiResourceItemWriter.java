/**
 * 
 */
package com.juxtapose.example.ch07.multiresource.ext;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.ResourceAwareItemWriterItemStream;
import org.springframework.batch.item.file.ResourceSuffixCreator;
import org.springframework.batch.item.file.SimpleResourceSuffixCreator;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-21下午05:50:51
 */
public class ExtMultiResourceItemWriter<T> extends AbstractItemStreamItemWriter<T> {

	final static private String RESOURCE_INDEX_KEY = "resource.index";

	final static private String CURRENT_RESOURCE_ITEM_COUNT = "resource.item.count";

	private Resource resource;

	private ResourceAwareItemWriterItemStream<? super T> delegate;

	private int itemCountLimitPerResource = Integer.MAX_VALUE;

	private int currentResourceItemCount = 0;

	private int resourceIndex = 1;

	private ResourceSuffixCreator suffixCreator = new SimpleResourceSuffixCreator();

	private boolean saveState = true;

	private boolean opened = false;

	public ExtMultiResourceItemWriter() {
		this.setExecutionContextName(ClassUtils.getShortName(MultiResourceItemWriter.class));
	}

    @Override
	public void write(List<? extends T> items) throws Exception {
		if (!opened) {
			File file = setResourceToDelegate();
			// create only if write is called
			//modify 20130921 begin
			if (file.getParent() != null) {
				new File(file.getParent()).mkdirs();
			}
			//modify 20130921 end
			file.createNewFile();
			Assert.state(file.canWrite(), "Output resource " + file.getAbsolutePath() + " must be writable");
			delegate.open(new ExecutionContext());
			opened = true;
		}
		delegate.write(items);
		currentResourceItemCount += items.size();
		if (currentResourceItemCount >= itemCountLimitPerResource) {
			delegate.close();
			resourceIndex++;
			currentResourceItemCount = 0;
			setResourceToDelegate();
			opened = false;
		}
	}

	/**
	 * Allows customization of the suffix of the created resources based on the
	 * index.
	 */
	public void setResourceSuffixCreator(ResourceSuffixCreator suffixCreator) {
		this.suffixCreator = suffixCreator;
	}

	/**
	 * After this limit is exceeded the next chunk will be written into newly
	 * created resource.
	 */
	public void setItemCountLimitPerResource(int itemCountLimitPerResource) {
		this.itemCountLimitPerResource = itemCountLimitPerResource;
	}

	/**
	 * Delegate used for actual writing of the output.
	 */
	public void setDelegate(ResourceAwareItemWriterItemStream<? super T> delegate) {
		this.delegate = delegate;
	}

	/**
	 * Prototype for output resources. Actual output files will be created in
	 * the same directory and use the same name as this prototype with appended
	 * suffix (according to
	 * {@link #setResourceSuffixCreator(ResourceSuffixCreator)}.
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setSaveState(boolean saveState) {
		this.saveState = saveState;
	}

    @Override
	public void close() throws ItemStreamException {
                super.close();
		resourceIndex = 1;
		currentResourceItemCount = 0;
		if (opened) {
			delegate.close();
		}
	}

    @Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
                super.open(executionContext);
		resourceIndex = executionContext.getInt(getExecutionContextKey(RESOURCE_INDEX_KEY), 1);
		currentResourceItemCount = executionContext.getInt(getExecutionContextKey(CURRENT_RESOURCE_ITEM_COUNT), 0);

		try {
			setResourceToDelegate();
		}
		catch (IOException e) {
			throw new ItemStreamException("Couldn't assign resource", e);
		}

		if (executionContext.containsKey(getExecutionContextKey(CURRENT_RESOURCE_ITEM_COUNT))) {
			// It's a restart
			delegate.open(executionContext);
		}
		else {
			opened = false;
		}
	}

    @Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
                super.update(executionContext);
		if (saveState) {
			if (opened) {
				delegate.update(executionContext);
			}
			executionContext.putInt(getExecutionContextKey(CURRENT_RESOURCE_ITEM_COUNT), currentResourceItemCount);
			executionContext.putInt(getExecutionContextKey(RESOURCE_INDEX_KEY), resourceIndex);
		}
	}

	/**
	 * Create output resource (if necessary) and point the delegate to it.
	 */
	private File setResourceToDelegate() throws IOException {
		String path = resource.getFile().getAbsolutePath() + suffixCreator.getSuffix(resourceIndex);
		File file = new File(path);
		delegate.setResource(new FileSystemResource(file));
		return file;
	}
}
