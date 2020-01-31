public class test
{
	

	public static void main(String[] args)
	{
		String	capitalized = "",
				letter = "",
				name = "dAvId mCcConnell";
		boolean capitalizeNext = true;
		
		for(int chr=0; chr < name.length(); chr++)
		{
			letter = name.substring(chr,chr+1); // letter is the letter at the given index
			
			if(capitalizeNext) {
				capitalized += letter.toUpperCase();
				capitalizeNext = false;
			}
			else {
				capitalized += letter.toLowerCase(); 
			}
			if(letter.equals(" ")) {
				capitalizeNext = true;
			}
		} // End for loop
		System.out.println(capitalized);
	}
	
}