pipeline {
    agent any

    tools {
        maven 'Maven-3.8.8'        // Refers to Maven installation in Global Tool Configuration
        dockerTool 'Docker'        // Refers to Docker installation in Global Tool Configuration
    }

    environment {
        DB_CREDENTIALS = credentials('db-credentials') // Jenkins credentials ID
        PATH = "/usr/local/bin:/usr/bin:/bin:$PATH"    // Ensure Docker CLI is in PATH
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

        stage('Debug Docker Compose') {
            steps {
                echo 'Debugging Docker Compose...'
                sh '''
                    echo "Validating Docker and Docker Compose installation..."
                    docker --version
                    docker compose version
                    echo "Checking current user and groups..."
                    whoami
                    groups
                    echo "Checking Docker socket permissions..."
                    ls -l /var/run/docker.sock
                '''
            }
        }

        stage('Run Application with Docker Compose') {
            steps {
                echo 'Starting PostgreSQL and Spring Boot...'
                sh '''
                    # Export environment variables
                    export DB_CREDENTIALS_USR=${DB_CREDENTIALS_USR}
                    export DB_CREDENTIALS_PSW=${DB_CREDENTIALS_PSW}

                    # Start services using docker compose
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
