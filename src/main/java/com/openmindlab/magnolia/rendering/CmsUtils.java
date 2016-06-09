package com.openmindlab.magnolia.rendering;

import info.magnolia.objectfactory.Components;
import info.magnolia.rendering.context.RenderingContext;
import info.magnolia.rendering.engine.RenderingEngine;
import info.magnolia.templating.elements.AreaElement;
import info.magnolia.templating.elements.ComponentElement;
import info.magnolia.templating.elements.PageElement;
import info.magnolia.templating.elements.TemplatingElement;

/**
 * Created by molaschi on 30/05/16.
 */
public class CmsUtils {

  public CmsUtils() {

  }

  public TemplatingElement createElement(String type) {
    final RenderingEngine renderingEngine = Components.getComponent(RenderingEngine.class);
    final RenderingContext renderingContext = renderingEngine.getRenderingContext();

    switch (type) {
      case "area":
        return Components.getComponentProvider().newInstance(AreaElement.class, renderingContext);

      case "page":
        return Components.getComponentProvider().newInstance(PageElement.class, renderingContext);

      case "component":
        return Components.getComponentProvider().newInstance(ComponentElement.class, renderingContext);
    }
    return null;
  }
}
