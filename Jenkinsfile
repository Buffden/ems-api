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
                    echo "### Validating Docker and Docker Compose Installation ###"

                    echo "Checking Docker version..."
                    docker --version || echo "Docker is not installed or not accessible"

                    echo "Checking Docker Compose versions..."
                    docker-compose --version || echo "docker-compose (V1 syntax) is not installed or not accessible"
                    docker compose version || echo "docker compose (V2 syntax) is not installed or not accessible"

                    echo "### Validating Environment ###"

                    echo "Current user and groups..."
                    whoami || echo "Unable to retrieve current user"
                    groups || echo "Unable to retrieve group membership"

                    echo "Docker socket permissions..."
                    ls -l /var/run/docker.sock || echo "Docker socket is not accessible"

                    echo "PATH Environment Variable..."
                    echo $PATH

                    echo "### Testing Docker Commands ###"

                    echo "Testing Docker access with 'docker ps'..."
                    docker ps || echo "Unable to execute Docker commands. Check Docker installation or permissions."

                    echo "Testing Docker Compose (V2) with 'docker compose ls'..."
                    docker compose ls || echo "docker compose (V2 syntax) failed. Check installation."

                    echo "Testing Docker Compose (V1) with 'docker-compose ls'..."
                    docker-compose ls || echo "docker-compose (V1 syntax) failed. Check installation."
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
