/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntradaSalida;

import ObjetosMoviles.Constantes;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author luis
 */
public class XMLParserCreditos {

    public static ArrayList<Creditos> LeerFichero() throws ParserConfigurationException, SAXException, IOException {

        ArrayList<Creditos> datosLista = new ArrayList<Creditos>();

        File f = new File(Constantes.ubicacioncreditos);
        if (!f.exists()) {
           // System.out.println("Archivo no encontrado en: " + f.getAbsolutePath());
            System.out.println("Directorio actual: " + System.getProperty("user.dir"));
        }

        DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factoria.newDocumentBuilder();
        Document d = builder.parse(f);

        String tema = null;
        String objeto = null;
        String creador = null;
        String licencia = null;
        String modificacion = null;

        NodeList principal = d.getElementsByTagName(Constantes.CREDITO);
        for (int i = 0; i < principal.getLength(); i++) {
            Node princ = principal.item(i);
            if (princ.getNodeType() == Node.ELEMENT_NODE) {
                Element ele = (Element) princ;
                tema = ele.getAttribute(Constantes.Tema);
                objeto = ele.getElementsByTagName(Constantes.OBJETO).item(0).getTextContent();
                creador = ele.getElementsByTagName(Constantes.CREADOR).item(0).getTextContent();

                licencia = ele.getElementsByTagName(Constantes.LICENCIA).item(0).getTextContent();
                modificacion = ele.getElementsByTagName(Constantes.MODIFICACION).item(0).getTextContent();
                datosLista.add(new Creditos(tema, objeto, creador, licencia, modificacion));
            }
        }
        return datosLista;
    }

    /*  public static void escribirFichero(ArrayList<Creditos> datosLista) throws ParserConfigurationException, IOException, TransformerException {
        File f = new File(Constantes.ubicacion);
        DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factoria.newDocumentBuilder();
        Document d = builder.newDocument();
        Element creditos = d.createElement(Constantes.CREDITOS);
        d.appendChild(creditos);
       
        
            Element credito = d.createElement(Constantes.CREDITO);
            credito.setAttribute("Tema","Musica");
            creditos.appendChild(creditos);
            
            Element objeto=d.createElement(Constantes.OBJETO);
            objeto

            
            
            tema.appendChild(d.createTextNode("MUSICA"));
            jugador.appendChild(nombre);

            Element puntos = d.createElement(Constantes.Puntos);
            puntos.appendChild(d.createTextNode(String.valueOf(dp.getPuntaje())));
            jugador.appendChild(puntos);

            Element fecha = d.createElement(Constantes.fecha);
            fecha.appendChild(d.createTextNode(dp.getFecha()));
            jugador.appendChild(fecha);
        
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        Source source = new DOMSource(d);
        FileWriter fw = new FileWriter(f);
        PrintWriter pw = new PrintWriter(fw);
        Result result = new StreamResult(pw);
        t.transform(source, result);

    }*/
}
