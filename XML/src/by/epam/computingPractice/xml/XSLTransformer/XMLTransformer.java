package by.epam.computingPractice.xml.XSLTransformer;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

public class XMLTransformer {
    private Transformer transformer;
    public XMLTransformer(String XSLFilePath) throws FileNotFoundException, TransformerConfigurationException {
        if (!Files.exists(Path.of(XSLFilePath))) {
            throw new FileNotFoundException("(Не удаётся найти указанный файл)");
        }
        TransformerFactory factory = TransformerFactory.newInstance();
        transformer = factory.newTransformer(new StreamSource(XSLFilePath));
    }

    public void transform(String XMLFilePath, String resultFileName) throws FileNotFoundException, TransformerException {
        if (!Files.exists(Path.of(XMLFilePath))) {
            throw new FileNotFoundException("(Не удаётся найти указанный файл)");
        }
        transformer.transform(new StreamSource(XMLFilePath), new StreamResult(resultFileName));
        System.out.println("Преобразование прошло успешно");
        System.out.println("Результат находится по пути " + Path.of(resultFileName).toAbsolutePath().normalize());
    }
}
