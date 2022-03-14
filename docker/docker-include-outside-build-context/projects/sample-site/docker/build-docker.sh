mkdir tmp-context
cp -R ../html tmp-context/
cp -R ../../config tmp-context/
cp Dockerfile-script tmp-context/Dockerfile
docker build -t sample-site:latest tmp-context
rm -rf tmp-context
