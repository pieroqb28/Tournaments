node{
    stage('SCM CHECKOUT'){
       
        git 'https://github.com/pieroqb28/Tournaments'
    }
    stage('COMPILE-PACKAGE'){
        bat 'mvn package'
    }
}