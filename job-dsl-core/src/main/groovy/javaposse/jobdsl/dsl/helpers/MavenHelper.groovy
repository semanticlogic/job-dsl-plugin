package javaposse.jobdsl.dsl.helpers

import com.google.common.base.Preconditions
import javaposse.jobdsl.dsl.JobParent
import javaposse.jobdsl.dsl.WithXmlAction

class MavenHelper extends AbstractHelper {

    Map<String, Object> jobArguments

    StringBuilder theGoals = new StringBuilder()
    StringBuilder theMavenOpts = new StringBuilder()
    boolean rootPOMAdded = false
    boolean perModuleEmailAdded = false
    boolean archivingDisabledAdded = false
    boolean runHeadlessAdded = false
    boolean ignoreUpstreamChangesAdded = false

    MavenHelper(List<WithXmlAction> withXmlActions, Map<String, Object> jobArguments = [:]) {
        super(withXmlActions)
        this.jobArguments = jobArguments
    }

    /**
     * Specifies the path to the root POM.
     * @param rootPOM path to the root POM
     */
    def rootPOM(String rootPOM) {
        Preconditions.checkState(jobArguments['type'] == JobParent.maven, "rootPOM can only be applied for Maven jobs")
        Preconditions.checkState(!rootPOMAdded, "rootPOM can only be applied once")
        rootPOMAdded = true
        execute {
            def node = methodMissing('rootPOM', rootPOM)
            it / node
        }
    }

    /**
     * Specifies the goals to execute.
     * @param goals the goals to execute
     */
    def goals(String goals) {
        Preconditions.checkState(jobArguments['type'] == JobParent.maven, "goals can only be applied for Maven jobs")
        if (theGoals.length() == 0) {
            theGoals.append(goals)
            execute {
                def node = methodMissing('goals', this.theGoals.toString())
                it / node
            }
        } else {
            theGoals.append(" ").append(goals)
            execute {}
        }
    }

    /**
     * Specifies the JVM options needed when launching Maven as an external process.
     * @param mavenOpts JVM options needed when launching Maven
     */
    def mavenOpts(String mavenOpts) {
        Preconditions.checkState(jobArguments['type'] == JobParent.maven, "mavenOpts can only be applied for Maven jobs")
        if (theMavenOpts.length() == 0) {
            theMavenOpts.append(mavenOpts)
            execute {
                def node = methodMissing('mavenOpts', this.theMavenOpts.toString())
                it / node
            }
        } else {
            theMavenOpts.append(" ").append(mavenOpts)
            execute {}
        }
    }

    /**
     * If set, Jenkins will send an e-mail notifications for each module, defaults to <code>true</code>.
     * @param perModuleEmail set to <code>false</code> to disable per module e-mail notifications
     */
    def perModuleEmail(boolean perModuleEmail) {
        Preconditions.checkState(jobArguments['type'] == JobParent.maven, "perModuleEmail can only be applied for Maven jobs")
        Preconditions.checkState(!perModuleEmailAdded, "perModuleEmail can only be applied once")
        perModuleEmailAdded = true
        execute {
            def node = methodMissing('perModuleEmail', perModuleEmail.toString())
            it / node
        }
    }

    /**
     * If set, Jenkins  will not automatically archive all artifacts generated by this project, defaults to
     * <code>false</code>.
     * @param archivingDisabled set to <code>true</code> to disable automatic archiving
     */
    def archivingDisabled(boolean archivingDisabled) {
        Preconditions.checkState(jobArguments['type'] == JobParent.maven, "archivingDisabled can only be applied for Maven jobs")
        Preconditions.checkState(!archivingDisabledAdded, "archivingDisabled can only be applied once")
        archivingDisabledAdded = true
        execute {
            def node = methodMissing('archivingDisabled', archivingDisabled.toString())
            it / node
        }
    }

    /**
     * Set to allow Jenkins to configure the build process in headless mode, defaults to <code>false</code>.
     * @param runHeadless set to <code>true</code> to run the build process in headless mode
     */
    def runHeadless(boolean runHeadless) {
        Preconditions.checkState(jobArguments['type'] == JobParent.maven, "runHeadless can only be applied for Maven jobs")
        Preconditions.checkState(!runHeadlessAdded, "runHeadless can only be applied once")
        runHeadlessAdded = true
        execute {
            def node = methodMissing('runHeadless', runHeadless.toString())
            it / node
        }
    }

    /**
     * If checked, Jenkins will parse the POMs of this project, and see if any of its snapshot dependencies are built on
     * this Jenkins as well. If so, Jenkins will set up build dependency relationship so that whenever the dependency
     * job is built and a new SNAPSHOT jar is created, Jenkins will schedule a build of this project. Defaults to
     * <code>false</code>.
     * @param ignoreUpstreamChanges set to <code>true</code> to ignore SNAPSHOT dependencies
     */
    def ignoreUpstreamChanges(boolean ignoreUpstreamChanges) {
        Preconditions.checkState(jobArguments['type'] == JobParent.maven, "ignoreUpstreamChanges can only be applied for Maven jobs")
        Preconditions.checkState(!ignoreUpstreamChangesAdded, "ignoreUpstreamChanges can only be applied once")
        ignoreUpstreamChangesAdded = true
        execute {
            def node = methodMissing('ignoreUpstremChanges', ignoreUpstreamChanges.toString())
            it / node
        }
    }
}
