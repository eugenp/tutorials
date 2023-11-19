pipeline {
    agent any
    stages {
        stage('Example') {
            steps {
                script {
                    def output = sh(script: "echo \$(ls)", returnStdout: true)
                    echo "Output: ${output}"
                }
            }
        }
    }
}
