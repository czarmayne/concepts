import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class SingletonTest {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IOException, CloneNotSupportedException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Singleton original = Singleton.getInstance();
        System.out.println("Hash code of original : " + original.hashCode());

        duplicate();

        System.out.println("=== Un/Breaking the Singleton Design Pattern ===");
        reflection();
        serializable(original);
        cloneable(original);

    }

    private static void duplicate() {
        Singleton duplicate = Singleton.getInstance();
        System.out.println("Hash code of duplicate : " + duplicate.hashCode());
    }

    private static void reflection() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Singleton reflection = null;
        Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        reflection = constructor.newInstance();
        System.out.println("Hash code of reflection : " + reflection.hashCode());
    }

    private static void serializable(Singleton original) throws IOException, ClassNotFoundException {
        String path = "Serializable.ser";
        ObjectOutputStream outSer = new ObjectOutputStream(new FileOutputStream(path));
        outSer.writeObject(original);
        outSer.close();

        ObjectInputStream inSer = new ObjectInputStream(new FileInputStream(path));
        Singleton serializable = (Singleton) inSer.readObject();

        System.out.println("Hash code of serializable : " + serializable.hashCode());
    }

    private static void cloneable(Singleton original) throws CloneNotSupportedException {
        Singleton clone = original.clone();
        System.out.println("Hash code of cloneable : " + clone.hashCode());
    }

}

public class Singleton implements Serializable, Cloneable {
    private static Singleton singleton;

    private Singleton() {
        //ensure that no new instance will be created
        if (singleton != null) {
            throw new IllegalStateException("Object cannot be created using Reflection");
        }
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    @Override
    public Singleton clone() throws CloneNotSupportedException {
//        return (Singleton) super.clone();
        throw new CloneNotSupportedException("Object cannot be created using Cloneable");
    }

    //to prevent serializable from breaking singleton
    protected Object readResolve() {
        return singleton;
    }
