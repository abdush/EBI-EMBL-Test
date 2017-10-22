#!/usr/bin/env bash

# This script will run under ProblemOne folder irrespective of where it was invoked from
cd $(dirname $0)

build() {
  # Do what you need to package your app, e.g. mvn package
  mvn clean package
}

run() {
  # Do what you need to run your app in the foreground
  # e.g. java -jar target/magic.jar $*
  java -jar target/problem-one.jar $*
}

usage() {
  cat <<EOF
Usage:
  $0 <command> <args>
Local machine commands:
  build  : builds and packages your app
  run    : starts your app in the foreground
EOF
}

action=$1
action=${action:-"usage"}
action=${action/help/usage}
shift
if type -t $action >/dev/null; then
  echo "Invoking: $action"
  $action $*
else
  echo "Unknown action: $action"
  usage
  exit 1
fi
