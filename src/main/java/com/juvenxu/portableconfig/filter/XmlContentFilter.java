package com.juvenxu.portableconfig.filter;

import com.juvenxu.portableconfig.ContentFilter;
import com.juvenxu.portableconfig.model.Replace;
import com.ximpleware.*;
import org.codehaus.plexus.component.annotations.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author juven
 */
@Component(role = ContentFilter.class, hint = "xml")
public class XmlContentFilter implements ContentFilter {

    @Override
    public boolean accept(String contentType) {
        return (".xml").equals(contentType);
    }

    @Override
    public void filter(File file, File tmpFile, List<Replace> replaces) throws IOException {
        try {
            VTDGen vg = new VTDGen();
            if (vg.parseFile(file.getName(), true)) {
                VTDNav vn = vg.getNav();
                AutoPilot ap = new AutoPilot(vn);
                XMLModifier xm = new XMLModifier(vn);
                int i = -1;
                for (Replace replace : replaces) {
                    ap.selectXPath(replace.getXpath());
                    while ((i = ap.evalXPath()) != -1) {
                        xm.updateToken(i, replace.getValue());
                    }
                    i = -1;
                }
                xm.output(tmpFile.getName());

            }
        } catch (NavException e) {
            System.out.println(" Exception during navigation " + e);
        } catch (ModifyException e) {
            System.out.println(" Modify exception occurred " + e);
        } catch (XPathEvalException e) {
            e.printStackTrace();
        } catch (TranscodeException e) {
            e.printStackTrace();
        } catch (XPathParseException e) {
            e.printStackTrace();
        }

    }

}
