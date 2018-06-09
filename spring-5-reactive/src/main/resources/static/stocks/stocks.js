function init() {
    $(document).ready(function() {
	setupStocksEventSrouce();
    });
}

function setupStocksEventSrouce() {
    var source = new EventSource('http://localhost:8080/stocks');
    source.addEventListener('stock-changed', function(e) {
	var updatedStocks = JSON.parse(e.data);
	updatedStocks.forEach(function(updatedStock) {
	    updateStock(updatedStock);
	});

    }, false);
}

function updateStock(stock) {
    console.log(stock);
    var stockDivContainer = $('.stocks-container');
    var stockDiv = $('#stock-' + stock.symbol);
    var stockDivInnerHtml = '<span class="current-price"><span class="currency-symbol">$</span>'
	    + stock.currentPrice
	    + '</span>\n'
	    + '<h1>'
	    + stock.name
	    + '</h1>\n' + '<h2>' + stock.symbol + '</h2>';

    if (stockDiv.length) {
	stockDiv.html(stockDivInnerHtml);
    } else {
	var stockDivHtml = '<div id="stock-' + stock.symbol
		+ '" class="stock-widget">\n' + stockDivInnerHtml + '\n</div>';
	$('.stocks-container').append($(stockDivHtml));
    }
}

init();