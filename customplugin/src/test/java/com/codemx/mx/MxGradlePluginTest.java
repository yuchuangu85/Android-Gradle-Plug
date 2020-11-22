/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.codemx.mx;

import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.api.Project;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A simple unit test for the 'com.codemx.mx.greeting' plugin.
 */
public class MxGradlePluginTest {
    @Test public void pluginRegistersATask() {
        // Create a test project and apply the plugin
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply("com.codemx.mx.greeting");

        // Verify the result
        assertNotNull(project.getTasks().findByName("greeting"));
    }
}