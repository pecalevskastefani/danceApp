pipeline {
  agent any
  tools {
    maven 'maven 3.8.7'
  }
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/pecalevskastefani/danceApp']]])
                bat 'mvn clean install'
            }
        }
       stage('Build docker image'){
            steps{
                script{
                    bat 'docker build -t stefpecal/danceapp .'
                }
            }
        }
        stage("Push image"){
            steps {
                bat 'docker push stefpecal/danceapp'
            }
        }
         stage('Deploy to K8S'){
            steps{
                script{
                   kubernetesDeploy configs:'deploymentservice.yaml', kubeConfig: [path: ''], kubeconfigId: 'k8sconfig', secretName: '', ssh: [sshCredentialsId: '*', sshServer: ''], textCredentials: [certificateAuthorityData: '', clientCertificateData: '', clientKeyData: '', serverUrl: 'https://']
                }
            }
        }
    }
}