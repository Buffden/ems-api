pipeline {
    agent any

    tools {
        maven 'Maven-3.8.8' // Match the name in the Global Tool Configuration
    }

    environment {
        DB_CREDENTIALS = credentials('db-credentials') // Jenkins credentials ID
    }

    stages {
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
                sh 'docker compose version' // Use `docker compose` instead of `docker-compose`
            }
        }

        stage('Run Application with Docker') {
            steps {
                echo 'Starting PostgreSQL and Spring Boot...'
                sh '''
                    # Export environment variables
                    export DB_CREDENTIALS_USR=${DB_CREDENTIALS_USR}
                    export DB_CREDENTIALS_PSW=${DB_CREDENTIALS_PSW}

                    # Start services
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
