pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        COMPOSE_PATH = "${WORKSPACE}/docker"
        SELENIUM_GRID = "true"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Start Selenium Grid') {
            steps {
                script {
                    echo "Starting Selenium Grid..."
                    bat "docker compose -f \"${COMPOSE_PATH}\\docker-compose.yml\" up -d"

                    echo "Waiting for Grid to start..."
                    sleep(time: 30, unit: 'SECONDS')
                }
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test -DseleniumGrid=true'
            }
        }

        stage('Reports') {
            steps {
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'src/test/resources/ExtentReport',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }

    post {

        always {

            script {
                echo "Stopping Selenium Grid..."
                bat "docker compose -f \"${COMPOSE_PATH}\\docker-compose.yml\" down"
            }

            archiveArtifacts(
                artifacts: '**/src/test/resources/ExtentReport/*.html',
                fingerprint: true
            )

            junit allowEmptyResults: true,
                  testResults: 'target/surefire-reports/*.xml'
        }

        success {
            emailext(
                to: 'anandphani17@gmail.com',
                subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                attachLog: true,
                body: """
                <html>
                <body>
                <p>Hello Team,</p>
                <p>The latest Jenkins build completed successfully.</p>

                <p><b>Project:</b> ${env.JOB_NAME}</p>
                <p><b>Build:</b> #${env.BUILD_NUMBER}</p>
                <p><b>Status:</b> <span style="color:green;"><b>SUCCESS</b></span></p>

                <p><b>Build URL:</b>
                <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>

                <p><b>Extent Report:</b>
                <a href="${env.BUILD_URL}Extent_20Report/">
                Click Here
                </a></p>

                <p>Best Regards,<br>
                Automation Team</p>

                </body>
                </html>
                """
            )
        }

        failure {
            emailext(
                to: 'anandphani17@gmail.com',
                subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                attachLog: true,
                body: """
                <html>
                <body>
                <p>Hello Team,</p>

                <p>The latest Jenkins build has
                <span style="color:red;"><b>FAILED</b></span>.</p>

                <p><b>Project:</b> ${env.JOB_NAME}</p>
                <p><b>Build:</b> #${env.BUILD_NUMBER}</p>

                <p><b>Build URL:</b>
                <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>

                <p>Please check Jenkins logs and reports.</p>

                <p><b>Extent Report:</b>
                <a href="${env.BUILD_URL}Extent_20Report/">
                Click Here
                </a></p>

                <p>Best Regards,<br>
                Automation Team</p>

                </body>
                </html>
                """
            )
        }
    }
}