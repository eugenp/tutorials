function fibonacci(index) {
	if (index <= 1)
		return index;
	return fibonacci(index-1) + fibonacci(index-2);
}

for (var i=0; i<100; i++) {
	var startTime = process.hrtime.bigint();
	var result = fibonacci(12);
	var totalTime = process.hrtime.bigint() - startTime;
	console.log(totalTime);
}

    