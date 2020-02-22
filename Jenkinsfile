node('master') {
    def date = new Date();
    def folder = date.format('MM-dd-yyyy-HH_mm_ss')


    ws(pwd() + '\\tests') {
        stage('Fetch Test SCM') {
            checkout changelog: false, poll: false, scm: [$class: 'GitSCM', branches: [
                [name: '*/master']
            ], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [
                [credentialsId: env.StashCredentialsId, url: 'https://github.com/vikasmathur100/daqaa.git']
            ]];
        }

        stage("Run Tests") {
                        def javaHome = tool(name: 'JDK.1.8', type: 'jdk');
            withEnv(["JAVA_HOME=${javaHome}"]) {
                bat "mvn clean verify -Dmaven.test.failure.ignore=true"            }
        }
    }

}