#!/bin/sh

if [[ "$OSTYPE" == "darwin"* ]]; then
  SED="sed -i '' -e"
else
  SED="sed -i -e"
fi

if [[ `uname -m` == "arm64" ]]; then
  MAVEN_PROFILE="-Parm64"
else
  MAVEN_PROFILE=""
fi

host=$(echo $HOSTNAME  | tr '[A-Z]' '[a-z]')

cd backend
sh ./mvnw clean install
sh ./mvnw -pl resource-server spring-boot:build-image -Dspring-boot.build-image.imageName=baeldung-bff/resource-server $MAVEN_PROFILE
sh ./mvnw -pl bff spring-boot:build-image -Dspring-boot.build-image.imageName=baeldung-bff/bff $MAVEN_PROFILE
cd ..

rm -f "compose-${host}.yml"
cp compose.yml "compose-${host}.yml"
$SED "s/LOCALHOST_NAME/${host}/g" "compose-${host}.yml"
rm -f "compose-${host}.yml''"

rm keycloak/import/baeldung-realm.json
cp baeldung-realm.json keycloak/import/baeldung-realm.json
$SED "s/LOCALHOST_NAME/${host}/g" keycloak/import/baeldung-realm.json
rm "keycloak/import/baeldung-realm.json''"

cd angular-ui/
rm src/app/app.config.ts
cp ../angular-ui.app.config.ts src/app/app.config.ts
$SED "s/LOCALHOST_NAME/${host}/g" src/app/app.config.ts
rm "src/app/app.config.ts''"
npm i
npm run build
cd ..

cd react-ui/
rm .env.development
cp ../react-ui.env.development .env.development
$SED "s/LOCALHOST_NAME/${host}/g" .env.development
rm ".env.development''"
npm i
npm run build
cd ..

cd vue-ui/
rm .env
cp ../vue-ui.env .env
$SED "s/LOCALHOST_NAME/${host}/g" .env
rm ".env''"
npm i
npm run build
cd ..

cd nginx-reverse-proxy/
rm nginx.conf
cp ../nginx.conf ./
$SED "s/LOCALHOST_NAME/${host}/g" nginx.conf
cd ..

docker build -t baeldung-bff/nginx-reverse-proxy ./nginx-reverse-proxy
docker build -t baeldung-bff/angular-ui ./angular-ui
docker build -t baeldung-bff/react-ui ./react-ui
docker build -t baeldung-bff/vue-ui ./vue-ui

docker compose -f compose-${host}.yml up -d

echo ""
echo "Open the following in a new private navigation window."

echo ""
echo "Keycloak as admin / admin:"
echo "http://${host}:7080/auth/admin/master/console/#/baeldung"

echo ""
echo "Sample frontends as user / user"
echo http://${host}:7080/angular-ui/
echo http://${host}:7080/react-ui/
echo http://${host}:7080/vue-ui/