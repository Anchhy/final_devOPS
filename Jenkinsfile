pipeline {
    agent any

    triggers {
        // Poll SCM every 5 minutes
        pollSCM('H/5 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                echo 'Building and testing the Spring Boot application...'
                // Make Maven wrapper executable
                sh 'chmod +x mvnw'
                // Run tests using the SQLite test database configuration
                sh './mvnw clean test -Dspring.profiles.active=test'
            }
        }

        stage('Deploy to Web Server via Ansible') {
            steps {
                echo 'Deploying to Web Server using Ansible Playbook...'
                // Ensure the Ansible inventory and playbook exist and run them
                sh 'ansible-playbook -i ansible/inventory.ini ansible/playbook.yml'
            }
        }
    }

    post {
        failure {
            echo 'Build failed! Sending email notifications...'
            // Send email to the developer who committed the error and CC the teacher
            emailext(
                subject: "Build ERROR: Job '${env.JOB_NAME}' [${env.BUILD_NUMBER}]",
                body: """
                    <p>FAILED: Job '${env.JOB_NAME}' [${env.BUILD_NUMBER}]</p>
                    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.BUILD_URL}</a>&QUOT;</p>
                """,
                mimeType: 'text/html',
                recipientProviders: [
                    [$class: 'CulpritsRecipientProvider'],
                    [$class: 'DevelopersRecipientProvider']
                ],
                to: 'srengty@gmail.com, etanchhy@gmail.com'
            )
        }
        success {
            echo 'Pipeline executed successfully!'
        }
    }
}
