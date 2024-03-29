package pl.com.zoo.main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pl.com.zoo.basic.Animal;
import pl.com.zoo.basic.Class;
import pl.com.zoo.data.FileManager;

public class ZooKeeper {

	protected Map<Class, Set<Animal>> animals = new HashMap<Class, Set<Animal>>();

	private FileManager fm = new FileManager("data/animals.bin");
	
	public ZooKeeper(String filename){
		fm = new FileManager(filename);
	}
	public ZooKeeper(){
		fm = new FileManager("data/animals.bin");
	}

	
	public boolean addAnimal(Class cl, Animal an) {
		if(cl == null){
			System.err.println("Klasa jest nullem, przerywam");
			return false;
		}
		boolean found = false;
		for (Class c : animals.keySet())
			if (c.equals(cl)) {
				animals.get(c).add(an);
				found = true;
			}
		if (!found) {
			Set<Animal> newSet = new HashSet<Animal>();
			newSet.add(an);
			animals.put(cl, newSet);
		}
		saveAnimalsToFile();
		return found;
	}
	
	public boolean removeAnimal(Animal an) {
		Animal toRemove = null;
		for (Set<Animal> s : animals.values()) {
			for(Animal a : s)
				if(a.equals(an))
					toRemove = a;
			if(toRemove!=null)
				s.remove(toRemove);
		}
		saveAnimalsToFile();
		return !(toRemove==null);
	}

	public void saveAnimalsToFile() {
		try {
			fm.saveAnimalsRegister(animals);
		} catch (Exception e) {
			System.out.println("Sorry, cos poszlo nie tak przy zapisie:");
			e.printStackTrace();
		}
	}

	public void readAnimalsFromFile() {
		try {
			animals = fm.readAnimalsRegister();
		} catch (Exception e) {
			System.out.println("Sorry, cos poszlo nie tak przy odczycie:");
			e.printStackTrace();
		}
	}

	public void makeAnimalsRegister() {
		Animal tiger = new Animal("Pimpus", "tiger", 200.5);
		Animal camel = new Animal("George", "camel", 253);
		Animal spider = new Animal("Puszek", "spider", 0.3);

		Class mammals = new Class("mammals");
		Class arachnids = new Class("arachnids");

		Set<Animal> mam = new HashSet<Animal>();
		mam.add(tiger);
		mam.add(camel);
		Set<Animal> ara = new HashSet<Animal>();
		ara.add(spider);

		animals.put(mammals, mam);
		animals.put(arachnids, ara);
	}

	public void printAnimals() {
		for (Class c : animals.keySet()) {
			System.out.println(c);
			
			for (Animal a : animals.get(c)) {
				System.out.println(a);
				
			}
		}
	}

}