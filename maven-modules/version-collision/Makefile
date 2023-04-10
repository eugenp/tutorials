.PHONY: common-setup cleanup

dependency-convergence:
	$(MAKE) common-setup
	$(MAKE) __default__
	$(MAKE) cleanup

dependency-convergence-enforce-inclusion:
	$(MAKE) common-setup
	$(MAKE) __include__
	$(MAKE) cleanup

dependency-convergence-enforce-exclusion:
	$(MAKE) common-setup
	$(MAKE) __exclude__
	$(MAKE) cleanup

__default__:
	# Generate pom.xml for enforce inclusion scenario
	echo "$$(cat dependencyconvergence.xml)\n</project>" >> pom.xml
	echo "pom.xml in use:"; cat pom.xml
	- mvn compile
	@#EXIT_CODE=$$?
	rm pom.xml
	mv pom.xml.v0 pom.xml
	@#exit $$EXIT_CODE

__include__:
	# Generate pom.xml for enforce inclusion scenario
	echo "$$(cat dependencyconvergence_include_scenario.xml)\n</project>" >> pom.xml
	echo "pom.xml in use:"; cat pom.xml
	- mvn compile
	rm pom.xml
	mv pom.xml.v0 pom.xml

__exclude__:
	# Generate pom.xml for enforce exclusion scenario
	echo "$$(cat dependencyconvergence_exclude_scenario.xml)\n</project>" >> pom.xml
	cat pom.xml
	- mvn compile
	rm pom.xml
	mv pom.xml.v0 pom.xml


common-setup:
	$(MAKE) cleanup
	cp pom.xml pom.xml.v0
	sed -i.v1 -e "/\<\/project\>/d" pom.xml
	sed -i.v2 -E -e 's#.*(\<dependencyManagement\>).*#<!-- &#' pom.xml
	sed -i.v3 -E -e 's#.*(\<\/dependencyManagement\>).*#&-->#' pom.xml

cleanup:
	rm -rf pom.xml.v*