#!/bin/sh

basedir=`dirname "$0"`

if [ -f "$basedir/mvnw" ]; then
  bindir="$basedir/target/node"
  repodir="$basedir/target/node/node_modules"
  installCommand="$basedir/mvnw --batch-mode -ntp -Pwebapp frontend:install-node-and-npm@install-node-and-npm"

  PATH="$basedir/$builddir/:$PATH"
  NPM_EXE="$basedir/$builddir/node_modules/npm/bin/npm-cli.js"
  NODE_EXE="$basedir/$builddir/node"
elif [ -f "$basedir/gradlew" ]; then
  bindir="$basedir/build/node/bin"
  repodir="$basedir/build/node/lib/node_modules"
  installCommand="$basedir/gradlew npmSetup"
else
  echo "Using npm installed globally"
  exec npm "$@"
fi

NPM_EXE="$repodir/npm/bin/npm-cli.js"
NODE_EXE="$bindir/node"

if [ ! -x "$NPM_EXE" ] || [ ! -x "$NODE_EXE" ]; then
  $installCommand || true
fi

if [ -x "$NODE_EXE" ]; then
  echo "Using node installed locally $($NODE_EXE --version)"
  PATH="$bindir:$PATH"
else
  NODE_EXE='node'
fi

if [ ! -x "$NPM_EXE" ]; then
  echo "Local npm not found, using npm installed globally"
  npm "$@"
else
  echo "Using npm installed locally $($NODE_EXE $NPM_EXE --version)"
  $NODE_EXE $NPM_EXE "$@"
fi
