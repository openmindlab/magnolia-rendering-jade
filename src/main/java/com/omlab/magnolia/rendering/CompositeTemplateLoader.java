package com.omlab.magnolia.rendering;

import de.neuland.jade4j.template.JadeTemplate;
import de.neuland.jade4j.template.TemplateLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by molaschi on 28/05/16.
 */
public class CompositeTemplateLoader implements TemplateLoader {
  private List<TemplateLoader> templateLoaders;

  public CompositeTemplateLoader() {
    templateLoaders = new ArrayList<>();
  }

  public CompositeTemplateLoader(TemplateLoader... templateLoaders) {
    this.templateLoaders = Arrays.asList(templateLoaders);
  }

  public List<TemplateLoader> getTemplateLoaders() {
    return templateLoaders;
  }

  public void setTemplateLoaders(List<TemplateLoader> templateLoaders) {
    this.templateLoaders = templateLoaders;
  }

  public void addTemplateLoader(TemplateLoader templateLoader) {
    templateLoaders.add(templateLoader);
  }

  @Override
  public long getLastModified(String s) throws IOException {
    for (TemplateLoader templateLoader : templateLoaders) {
      try {
        long lastModified = templateLoader.getLastModified(s);
        if (lastModified > 0) {
          return lastModified;
        }
      } catch (IOException e) {
        // TODO: composite exception http://stackoverflow.com/questions/12481583/exception-composition-in-java-when-both-first-strategy-and-recovery-strategy-fai
      }
    }
    return 0;
  }

  @Override
  public Reader getReader(String s) throws IOException {

    for (TemplateLoader templateLoader : templateLoaders) {
      try {
        Reader templateReader = templateLoader.getReader(s.indexOf('/') == 0 ? s.substring(1) : s);
        if (templateReader != null) {
          return templateReader;
        }
      } catch (IOException e) {
        // TODO: composite exception http://stackoverflow.com/questions/12481583/exception-composition-in-java-when-both-first-strategy-and-recovery-strategy-fai
      }
    }
    throw new FileNotFoundException(s);
  }
}
