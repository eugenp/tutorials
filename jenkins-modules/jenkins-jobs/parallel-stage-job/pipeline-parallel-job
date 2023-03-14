pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'echo "Building the application"'
                // Add commands to build application
            }
        }
        stage('Test') {
            parallel {
                stage('Unit Tests') {
                    steps {
                        sh 'sleep 5s'
                        sh 'echo "Running unit tests"'
                        // Add commands to run unit tests
                    }
                }
                stage('Integration Tests') {
                    steps {
                        sh 'echo "Running integration tests"'
                        // Add commands to run integration tests
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                sh 'echo "Deploying the application"'
                // Add commands to deploy application
            }
        }
    }
}
