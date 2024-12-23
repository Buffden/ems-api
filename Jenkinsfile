pipeline {
    agent any

    tools {
        maven 'Maven-3.8.8'
    }

    environment {
        DB_CREDENTIALS = credentials('db-credentials')
    }

    stages {
        stage('Debug Docker') {
            steps {
                sh '''
                    docker --version
                    docker compose version
                '''
            }
        }
        stage('Clone Repository') {
            steps {
                echo 'Cloning Repository...'
                git branch: 'main', url: 'https://github.com/Buffden/ems-api'
            }
        }

        stage('Build Application') {
            steps {
                echo 'Building the application...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Validate Docker Compose') {
            steps {
                sh 'docker compose version' // Use `docker compose`
            }
        }

        stage('Run Application with Docker') {
            steps {
                echo 'Starting PostgreSQL and Spring Boot...'
                sh '''
                    export DB_CREDENTIALS_USR=${DB_CREDENTIALS_USR}
                    export DB_CREDENTIALS_PSW=${DB_CREDENTIALS_PSW}
                    docker compose up -d --build
                '''
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
            }
        }

        stage('Stop Services') {
            steps {
                echo 'Stopping services...'
                sh 'docker compose down'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Please check the logs.'
        }
    }
}
