pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                echo 'Cloning Repository...'
                git branch: 'main', url: 'https://github.com/Buffden/ems-api'
            }
        }

        stage('Build with Maven') {
            steps {
                echo 'Building the project...'
                sh 'mvn clean package'
            }
        }

        stage('Dockerize Application') {
            steps {
                echo 'Building Docker Image...'
                sh '''
                    docker build -t spring-boot-app .
                    docker tag spring-boot-app buffden/spring-boot-app:latest
                '''
            }
        }

        stage('Run Docker Container') {
            steps {
                echo 'Running Docker Container...'
                sh 'docker run -d -p 8081:8080 --name backend-container spring-boot-app'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
