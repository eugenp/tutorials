CREATE TABLE IF NOT EXISTS stock_prices (
	symbol String,
	original_price Float64,
	currency String,
	timestamp DateTime64(9, 'UTC')
) ENGINE = MergeTree() ORDER BY (symbol, timestamp);

CREATE TABLE IF NOT EXISTS avg_stock_prices (
    date_time DateTime64(9, 'UTC'),
    symbol String,
    currency String,
    avg_price AggregateFunction(avg, Float64)
) ENGINE = AggregatingMergeTree()
ORDER BY (symbol, currency, date_time);

DROP TABLE IF EXISTS avg_stock_prices_mv;

CREATE MATERIALIZED VIEW avg_stock_prices_mv TO avg_stock_prices AS
SELECT
    toStartOfMinute(timestamp) AS date_time,
    symbol,
	currency,
    avgState(original_price) AS avg_price
FROM stock_prices
GROUP BY symbol, currency, date_time;


