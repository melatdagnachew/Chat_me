pipeline {
  agent any
  stages {
     stage('Lint & Unit Test') {
      parallel {                                 (***)
        stage('checkStyle') {
          steps {
            // We use checkstyle gradle plugin to perform this
            sh './gradlew checkStyle'
          }
        }

        stage('Unit Test') {
          steps {
            // Execute your Unit Test
            sh './gradlew testStagingDebug'
          }
        }
      }
    }
    stage('build') {
      steps {
        echo 'Melat\'s development pipeline'
      }
    }
  }
}
