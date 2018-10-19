package com.baeldung.spliteratorAPI;

import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class RelatedAuthorSpliterator implements Spliterator<Author> {
	private final List<Author> list;
	AtomicInteger current = new AtomicInteger();

	public RelatedAuthorSpliterator(List<Author> list) {
		this.list = list;
	}

	@Override
	public boolean tryAdvance(Consumer<? super Author> action) {

		action.accept(list.get(current.getAndIncrement()));
		return current.get() < list.size();
	}

	@Override
	public Spliterator<Author> trySplit() {
		int currentSize = list.size() - current.get();
		if (currentSize < 10) {
			return null;
		}
		for (int splitPos = currentSize / 2 + current.intValue(); splitPos < list.size(); splitPos++) {
			if (list.get(splitPos).getRelatedArticleId() == 0) {
				Spliterator<Author> spliterator = new RelatedAuthorSpliterator(list.subList(current.get(), splitPos));
				current.set(splitPos);
				return spliterator;
			}
		}
		return null;
	}

	@Override
	public long estimateSize() {
		return list.size() - current.get();
	}

	@Override
	public int characteristics() {
		return CONCURRENT;
	}

}
