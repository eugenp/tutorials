=========

## Spring Data MongoDB Live Testing


There are 3 scripts to simplify running live tests:
1. `live-test-setup.sh` builds a docker image with the necessary setup and runs it. The environment is ready, when the log stops - it takes approximately 30 seconds.
2. `live-test.sh` runs the live tests (but no other tests).
3. `live-test-setup.sh` stops and removes the docker image.
