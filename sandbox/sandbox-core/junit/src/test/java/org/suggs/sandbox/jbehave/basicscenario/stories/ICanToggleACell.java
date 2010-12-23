/*
 * ICanToggleACell.java created on 24 Aug 2010 19:23:27 by suggitpe for project sandbox-junit
 * 
 */
package org.suggs.sandbox.jbehave.basicscenario.stories;

import org.suggs.sandbox.jbehave.basicscenario.steps.GridSteps;
import org.suggs.sandbox.jbehave.support.SuggsMostUsefulConfiguration;

import java.util.List;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.CasePreservingResolver;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO Write javadoc for ICanToggleACell
 * 
 * @author suggitpe
 * @version 1.0 24 Aug 2010
 */
public class ICanToggleACell extends JUnitStory {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger( ICanToggleACell.class );

    public ICanToggleACell() {}

    @Override
    public List<CandidateSteps> candidateSteps() {
        InstanceStepsFactory factory = new InstanceStepsFactory( configuration(), new GridSteps() );
        return factory.createCandidateSteps();
    }

    /**
     * Constructs a new instance.
     */
    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        Configuration config = new SuggsMostUsefulConfiguration();
        config.useStoryLoader( new LoadFromClasspath( embeddableClass ) );
        StoryPathResolver storyPathResolver = new CasePreservingResolver();
        config.useStoryPathResolver( storyPathResolver );
        return config;
    }

}
