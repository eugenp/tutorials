---
name: article-summarizer
description: Summarizes articles into concise digests. Useful when user asks to summarize or get key points from an article.
---
# Article Summarizer
## Instructions
When summarizing an article:
1. If given a URL: Run `uv run scripts/fetch_article.py <url>` to retrieve the content.
2. Once content is available, extract the main thesis, few key points, and conclusion.
3. Structure the output as a TL;DR, key points, and a bottom line.