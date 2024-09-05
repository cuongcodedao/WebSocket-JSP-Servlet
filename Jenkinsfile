pipeline {
    agent any

    parameters {
        choice(name: 'ACTION', choices: ['Build', 'Remove all'], description: 'Pick something')
    }

    stages {
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
            cleanWs()  // Clean workspace after the pipeline finishes
        }
    }
}
