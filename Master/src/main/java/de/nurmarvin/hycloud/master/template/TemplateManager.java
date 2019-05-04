package de.nurmarvin.hycloud.master.template;

import java.util.Set;

/**
 * Manages all the templates
 * @author NurMarvin
 * @see Template
 */
public interface TemplateManager {

    /**
     * Adds a template to the template set
     * @param template the template to add
     */
    void addTemplate(Template template);

    /**
     * Removes a template from the template set
     * @param template the template to remove
     */
    void removeTemplate(Template template);

    /**
     * Gets the template with the provided name
     * @param name the name of the template
     * @return the template with the provided name if found otherwise null
     */
    Template getTemplateByName(String name);

    /**
     * A set of all templates
     * @return the set of all templates
     */
    Set<Template> templates();

    /**
     * Saves all the templates
     */
    void save();

    /**
     * Loads all templates
     */
    void load();
}
