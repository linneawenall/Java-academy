


/* Construct the commands of the 'narrow' interface 
 * from the wrapped device's interface. These are 
 * indicated in the Tests (4.) section.*/
public interface DeviceNarrow {


	public Object execute (String command, Object [] params);
	
	public boolean isOn();

	public boolean isRamping();


	
}
