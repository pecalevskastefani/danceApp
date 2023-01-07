pipeline {
  agent any
  tools {
    maven 'maven 3.8.7'
  }
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/pecalevskastefani/danceApp']]])
                sh 'mvn clean install'
            }
        }
       stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t stefpecal/danceapp .'
                }
            }
        }
        stage("Push image"){
            steps {
                withCredentials([string(credentialsId: 'dockerhub', variable: 'dockerhub')]) {
                    sh 'docker login -u stefpecal -p ${dockerhub}'
                }
                sh 'docker push stefpecal/danceapp'
            }
        }
    }
}