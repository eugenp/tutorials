/**
 * 
 */
package com.juxtapose.example.ch05.step.listener;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2012-9-1上午08:07:17
 */
public class SystemOutChunkListener implements ChunkListener {

//	/* (non-Javadoc)
//	 * @see org.springframework.batch.core.ChunkListener#beforeChunk()
//	 */
//	public void beforeChunk() {
//		System.out.println("ChunkListener.beforeChunk()");
//	}
//
//	/* (non-Javadoc)
//	 * @see org.springframework.batch.core.ChunkListener#afterChunk()
//	 */
//	public void afterChunk() {
//		System.out.println("ChunkListener.afterChunk()");
//	}

	@Override
	public void beforeChunk(ChunkContext context) {
		System.out.println("ChunkListener.beforeChunk()");
	}

	@Override
	public void afterChunk(ChunkContext context) {
		System.out.println("ChunkListener.afterChunk()");
	}

	@Override
	public void afterChunkError(ChunkContext context) {
		System.out.println("ChunkListener.afterChunkError()");
	}

}
