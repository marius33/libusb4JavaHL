package structures;

import org.usb4java.DeviceList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Marius on 24/07/2016.
 */
public class UsbDeviceList implements Iterable<UsbDevice> {

    private List<UsbDevice> devices;

    protected UsbDeviceList(int size, DeviceList list) {

        devices = new ArrayList(size);
        for (int i = 0; i < size; i++)
            devices.add(new UsbDevice(list.get(i)));

    }

    public UsbDeviceList filter(int vid, int pid){
        for(int i=0; i<devices.size(); i++){
            if(((devices.get(i).vid()!=vid) && (vid>=0)) || ((devices.get(i).pid()!=pid) && (pid>=0))){
                devices.get(i).unref();
                devices.remove(i);
            }
        }
        return this;
    }

    public int size(){
        return devices.size();
    }

    public UsbDevice get(int index){
        return devices.get(index);
    }

    public void freeUnusedDevices(){
        for(int i=0; i<devices.size(); i++){
            devices.get(i).unref();
            if(!devices.get(i).isActive())
                devices.remove(i);
        }
    }

    @Override
    public Iterator<UsbDevice> iterator() {
        Iterator<UsbDevice> it = new Iterator<UsbDevice>() {

            private int pos = 0;

            @Override
            public boolean hasNext() {
                return pos<devices.size();
            }

            @Override
            public UsbDevice next() {
                return devices.get(pos++);
            }
        };

        return it;
    }

}
