
class FirstSpecification extends Specification  {

	def "one plus one should equal two"() {
	    given:
	        int left = 2
	        int right = 2

	    when:
	        int result = left + right

	    then:
	        result == 4
	        
	}
}