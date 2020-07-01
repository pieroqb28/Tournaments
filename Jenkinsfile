pipeline {
    agent any
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Build') {
            steps {
                bat './mvnw spring-boot:run'
            }
        }
    }
}