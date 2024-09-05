pipeline {
    agent any

    parameters {
        choice(name: 'ACTION', choices: ['Build', 'Remove all'], description: 'Pick something')
    }

    stages {
        stage('Clean Workspace') {
            steps {
                // Manually clean the workspace before proceeding
                sh 'rm -rf /var/jenkins_home/workspace/Deploy_to_DEV_master/* || true'
            }
        }

        stage('Checkout SCM') {
            steps {
                // Checkout the Git repository
                checkout scm
            }
        }

        stage('Building/Deploying') {
            when {
                expression { return params.ACTION == 'Build' }
            }
            steps {
                withDockerRegistry(credentialsId: 'dockerhub', url: 'https://index.docker.io/v1/') {
                    sh 'docker compose up -d --build'
                    sh '''
                    docker images | grep web_chat | awk '{print $1":"$2}' | xargs -I {} docker push {}
                    '''
                }
            }
        }

        stage('Removing all') {
            when {
                expression { return params.ACTION == 'Remove all' }
            }
            steps {
                sh 'docker compose down -v'
            }
        }
    }

    post {
        always {
            // Clean workspace after the pipeline finishes
            cleanWs()  // Requires Workspace Cleanup Plugin
        }
    }
}
