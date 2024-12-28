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

                    echo "Checking Docker Compose version..."
                    docker-compose --version || echo "docker-compose is not installed or not accessible"

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

                    echo "Testing Docker Compose with 'docker-compose ls'..."
                    docker-compose ls || echo "docker-compose failed. Check installation."
                '''
            }
        }

        stage('Run Application with Docker Compose') {
            steps {
                echo 'Starting PostgreSQL and Spring Boot...'
                sh '''
                    # Check if the postgres container already exists
                    if docker ps -a --filter name=postgres | grep -w postgres; then
                        echo "Using existing PostgreSQL container..."
                    else
                        echo "Starting a new PostgreSQL container..."
                        docker-compose up -d --build postgres
                    fi

                    # Start the other services
                    docker-compose up -d --build jenkins app
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
                sh 'docker-compose down'
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
