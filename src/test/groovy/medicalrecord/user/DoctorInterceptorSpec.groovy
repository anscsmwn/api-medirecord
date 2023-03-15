package medicalrecord.user

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class DoctorInterceptorSpec extends Specification implements InterceptorUnitTest<DoctorInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test doctor interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"doctor")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
