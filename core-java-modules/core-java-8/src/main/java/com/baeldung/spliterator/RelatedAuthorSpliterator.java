package com.baeldung.spliterator;

import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class RelatedAuthorSpliterator implements Spliterator<Author> {
	private final List<Author> list;
	int current;

	private int end;

	public RelatedAuthorSpliterator(List<Author> list) {
		this.list = list;
		end =  list.size()-1;
	}

	public RelatedAuthorSpliterator(List<Author> list, int start, int end) {
		this.list = list;
		this.current=start;
		this.end= end;
	}

	@Override
	public boolean tryAdvance(Consumer<? super Author> action) {
		if (current <= end) {
			action.accept(list.get(current));
			current++;
			return true;
		}
		return false;
	}

	@Override
	public Spliterator<Author> trySplit() {
		/*int currentSize = list.size() - current.get();
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
		return null;*/
		if((end - current) < 100){
			return null;
		}

		int splitPos = current + (end -current)/2;

		int relatedArticleBeforePos = list.get(splitPos-1).getRelatedArticleId();
		int relatedArticleAfterPos = list.get(splitPos).getRelatedArticleId();

		while(relatedArticleBeforePos == relatedArticleAfterPos){
			splitPos++;
			relatedArticleBeforePos = relatedArticleAfterPos;
			relatedArticleAfterPos = list.get(splitPos).getRelatedArticleId();
		}

		RelatedAuthorSpliterator secondHlf = new RelatedAuthorSpliterator(list,splitPos,end);
		end = splitPos -1;
		return secondHlf;
	}

	@Override
	public long estimateSize() {
		return end - current;
	}

	@Override
	public int characteristics() {
		return CONCURRENT;
	}

}
