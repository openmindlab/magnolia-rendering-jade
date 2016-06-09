package com.omlab.magnolia.rendering;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.expression.JsExpressionHandler;
import de.neuland.jade4j.template.ClasspathTemplateLoader;
import de.neuland.jade4j.template.FileTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;
import de.neuland.jade4j.template.TemplateLoader;
import info.magnolia.cms.beans.config.ServerConfiguration;
import info.magnolia.cms.core.SystemProperty;
import info.magnolia.context.MgnlContext;
import info.magnolia.context.WebContext;
import info.magnolia.i18nsystem.TranslationService;
import info.magnolia.init.MagnoliaConfigurationProperties;
import info.magnolia.init.MagnoliaServletContextListener;
import info.magnolia.objectfactory.Components;
import info.magnolia.rendering.context.RenderingContext;
import info.magnolia.rendering.engine.RenderException;
import info.magnolia.rendering.engine.RenderingEngine;
import info.magnolia.rendering.renderer.AbstractRenderer;
import info.magnolia.rendering.template.RenderableDefinition;
import info.magnolia.rendering.util.AppendableWriter;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.io.InputStreamReader;

/**
 * Created by molaschi on 27/05/16.
 */
public class JadeRenderer extends AbstractRenderer {

  private final TranslationService translationService;
  private final ServerConfiguration serverConfiguration;


  @Inject
  public JadeRenderer(RenderingEngine renderingEngine, TranslationService translationService, ServerConfiguration serverConfiguration) {
    super(renderingEngine);
    this.translationService = translationService;
    this.serverConfiguration = serverConfiguration;
  }

  @Override
  protected Map<String, Object> newContext() {
    return new HashMap<>();
  }

  @Override
  protected void onRender(Node node, RenderableDefinition renderableDefinition, RenderingContext renderingContext, Map<String, Object> ctx, String templateScript) throws RenderException {
    final Locale locale = MgnlContext.getAggregationState().getLocale();
    final JadeConfiguration configuration = Components.getComponent(JadeConfiguration.class);

    try {
      AppendableWriter out = renderingContext.getAppendable();
      prepareData(ctx, locale);
      ctx.put("__out", out);
      configuration.renderTemplate(configuration.getTemplate(templateScript), ctx, out);
    } catch (IOException e) {
      throw new RenderException(e);
    }
  }

  protected void prepareData(Map<String, Object> data, Locale locale) {
    if (MgnlContext.hasInstance()) {
      data.put("ctx", MgnlContext.getInstance());
    }

    if (MgnlContext.isWebContext()) {
      WebContext webCtx = MgnlContext.getWebContext();
      data.put("contextPath", webCtx.getContextPath());
      data.put("aggregationState", webCtx.getAggregationState());
      // this.addTaglibSupportData(data, webCtx);
    }

    data.put("defaultBaseUrl", this.serverConfiguration.getDefaultBaseUrl());
    data.put("i18n", new MessagesWrapper(locale, this.translationService));
    data.put("i18nAuthoring", new MessagesWrapper(MgnlContext.getLocale(), this.translationService));
    data.put("cms", new CmsUtils());
  }
}
