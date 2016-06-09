package com.omlab.magnolia.rendering;

import de.neuland.jade4j.template.TemplateLoader;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.FileInputStream;

/**
 * Created by molaschi on 31/05/16.
 */
public class WebappTemplateLoader implements TemplateLoader {

  private String encoding = "UTF-8";
  private String basePath = "";
  private String webappPath = "";

  @Inject
  public WebappTemplateLoader(ServletContext servletContext) {
    this.webappPath = servletContext.getRealPath("/");
  }

  public long getLastModified(String name) {
    File templateSource = getFile(name);
    return templateSource.lastModified();
  }

  @Override
  public Reader getReader(String name) throws IOException {
    File templateSource = getFile(name);
    return new InputStreamReader(new FileInputStream(templateSource), this.getEncoding());
  }

  private File getFile(String name) {
    // TODO Security
    return new File(new File(webappPath, this.getBasePath()), name);
  }

  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public String getBasePath() {
    return basePath;
  }

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }
}
