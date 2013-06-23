package com.example.hata.contentprovider;


import com.example.hata.data.Dish;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Максим
 * Date: 06.03.13
 * Time: 8:36
 * To change this template use File | Settings | File Templates.
 */
public class ItemXMLHandler extends DefaultHandler {

    Boolean currentElement = false;
    String currentValue = "";
    Dish item = null;

    private ArrayList<Dish> disheslist = new ArrayList<Dish>();

    public ArrayList<Dish> getDisheslist(){
        return disheslist;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        //To change body of overridden methods use File | Settings | File Templates.
        currentElement = true;
        currentValue = "";
        if (localName.equals("dish")) {
            item = new Dish();

        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        //To change body of overridden methods use File | Settings | File Templates.

        currentElement=false;
        if (localName.equalsIgnoreCase("Name"))
            item.setName(currentValue);
        else if (localName.equalsIgnoreCase("Price"))
            item.setPrice(Double.parseDouble(currentValue));
            else if (localName.equalsIgnoreCase("Description"))
                item.setDescription(currentValue);
                else if (localName.equalsIgnoreCase("Weight"))
                     item.setWeight(Integer.parseInt(currentValue));
                    else    if (localName.equalsIgnoreCase("Image"))
                            item.setImage(currentValue);
                            else if (localName.equalsIgnoreCase("Category")){
                                    item.setCategory(currentValue);
                                    disheslist.add(item);
                                    }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        //To change body of overridden methods use File | Settings | File Templates.

      if (currentElement){
          currentValue=currentValue + new String(ch, start, length);
      }

    }
}
