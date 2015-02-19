/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.tinkerpop.gremlin.process;

import org.apache.tinkerpop.gremlin.AbstractGremlinSuite;
import org.apache.tinkerpop.gremlin.AbstractGremlinTest;
import org.apache.tinkerpop.gremlin.process.computer.GraphComputerTest;
import org.apache.tinkerpop.gremlin.process.computer.ranking.PageRankVertexProgramTest;
import org.apache.tinkerpop.gremlin.process.computer.util.ComputerDataStrategyTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.branch.BranchTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.branch.ChooseTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.branch.LocalTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.branch.RepeatTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.branch.UnionTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.AndTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.CoinTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.CyclicPathTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.DedupTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.ExceptTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.FilterTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.HasNotTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.HasTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.IsTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.OrTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.RangeTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.RetainTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.SampleTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.SimplePathTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.filter.WhereTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.BackTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.CoalesceTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.CountTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.FoldTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.MapTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.MaxTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.MeanTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.MinTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.OrderTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.PathTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.PropertiesTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.SelectTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.SumTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.UnfoldTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.ValueMapTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.map.VertexTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.sideEffect.AggregateTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.sideEffect.GroupCountTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.sideEffect.GroupTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.sideEffect.InjectTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.sideEffect.ProfileTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.sideEffect.SackTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.sideEffect.SideEffectCapTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.sideEffect.StoreTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.step.sideEffect.TreeTest;
import org.apache.tinkerpop.gremlin.process.graph.traversal.strategy.TraversalVerificationStrategyTest;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The {@code ProcessComputerStandardSuite} is a JUnit test runner that executes the Gremlin Test Suite over a
 * {@link org.apache.tinkerpop.gremlin.structure.Graph} implementation.  This specialized test suite and runner is for use
 * by Gremlin implementers to test their {@link org.apache.tinkerpop.gremlin.structure.Graph} implementations.  The
 * {@code ProcessComputerStandardSuite} ensures consistency and validity of the implementations that they test.
 * <p/>
 * To use the {@code ProcessComputerStandardSuite} define a class in a test module.  Simple naming would expect the
 * name of the implementation followed by "ProcessComputerStandardSuite".  This class should be annotated as follows
 * (note that the "Suite" implements ProcessComputerStandardSuite.GraphProvider as a convenience only. It could be
 * implemented in a separate class file):
 * <code>
 *
 * @author Stephen Mallette (http://stephen.genoprime.com)
 * @RunWith(ProcessComputerSuite.class)
 * @ProcessComputerSuite.GraphProviderClass(TinkerGraphProcessComputerTest.class) public class TinkerGraphProcessComputerTest implements GraphProvider {
 * }
 * </code>
 * Implementing {@link org.apache.tinkerpop.gremlin.GraphProvider} provides a way for the {@code ProcessComputerStandardSuite}
 * to instantiate {@link org.apache.tinkerpop.gremlin.structure.Graph} instances from the implementation being tested to
 * inject into tests in the suite.  The {@code ProcessComputerStandardSuite} will utilized Features defined in the
 * suite to determine which tests will be executed.
 * <br/>
 */
public class ProcessComputerSuite extends AbstractGremlinSuite {

    // todo: all tests are not currently passing. see specific todos in each test

    /**
     * This list of tests in the suite that will be executed.  Gremlin developers should add to this list
     * as needed to enforce tests upon implementations.
     */
    private static final Class<?>[] allTests = new Class<?>[]{

            // basic api semantics testing
            // GraphComputerTest.ComputerTest.class,   // todo: not sure this should be here as it forces retest of GraphComputer without an "implementation"

            // branch
            BranchTest.StandardTest.class,
            ChooseTest.StandardTest.class,
            LocalTest.StandardTest.class,
            RepeatTest.StandardTest.class,
            UnionTest.StandardTest.class,

            // filter
            AndTest.StandardTest.class,
            CoinTest.StandardTest.class,
            CyclicPathTest.StandardTest.class,
            DedupTest.StandardTest.class,
            ExceptTest.StandardTest.class,
            FilterTest.StandardTest.class,
            HasNotTest.StandardTest.class,
            HasTest.StandardTest.class,
            IsTest.StandardTest.class,
            OrTest.StandardTest.class,
            RangeTest.StandardTest.class,
            RetainTest.StandardTest.class,
            SampleTest.StandardTest.class,
            SimplePathTest.StandardTest.class,
            WhereTest.StandardTest.class,

            // map
            BackTest.StandardTest.class,
            CountTest.StandardTest.class,
            FoldTest.StandardTest.class,
            MapTest.StandardTest.class,
            MaxTest.StandardTest.class,
            MeanTest.StandardTest.class,
            MinTest.StandardTest.class,
            SumTest.StandardTest.class,
            // TODO: MatchTest.ComputerTest.class,
            OrderTest.StandardTest.class,
            PathTest.StandardTest.class,
            PropertiesTest.StandardTest.class,
            SelectTest.StandardTest.class,
            UnfoldTest.StandardTest.class,
            ValueMapTest.StandardTest.class,
            VertexTest.StandardTest.class,
            CoalesceTest.StandardTest.class,

            // sideEffect
            // TODO: AddEdgeTest.ComputerTest.class,
            AggregateTest.StandardTest.class,
            GroupTest.StandardTest.class,
            GroupCountTest.StandardTest.class,
            // TODO: InjectTest.ComputerTest.class,
            ProfileTest.StandardTest.class,
            SackTest.StandardTest.class,
            SideEffectCapTest.StandardTest.class,
            // TODO: REMOVE? SideEffectTest.ComputerTest.class,
            StoreTest.StandardTest.class,
            // TODO: REMOVE? SubgraphTest.ComputerTest.class,
            TreeTest.StandardTest.class,

            // algorithms
            PageRankVertexProgramTest.class,

            // strategy
            ComputerDataStrategyTest.class,

            // strategy
            TraversalVerificationStrategyTest.ComputerTest.class
    };

    /**
     * Tests that will be enforced by the suite where instances of them should be in the list of testsToExecute.
     */
    protected static final Class<?>[] testsToEnforce = new Class<?>[]{
            // basic api semantics testing
            GraphComputerTest.class,

            // branch
            BranchTest.class,
            ChooseTest.class,
            LocalTest.class,
            RepeatTest.class,
            UnionTest.class,

            // filter
            AndTest.class,
            CoinTest.class,
            CyclicPathTest.class,
            DedupTest.class,
            ExceptTest.class,
            FilterTest.class,
            HasNotTest.class,
            HasTest.class,
            IsTest.class,
            OrTest.class,
            RangeTest.class,
            RetainTest.class,
            SampleTest.class,
            SimplePathTest.class,
            WhereTest.class,


            // map
            BackTest.class,
            CountTest.class,
            // FoldTest.class,
            MapTest.class,
            MaxTest.class,
            MeanTest.class,
            MinTest.class,
            SumTest.class,
            // MatchTest.class,
            OrderTest.class,
            PathTest.class,
            PropertiesTest.class,
            SelectTest.class,
            UnfoldTest.class,
            ValueMapTest.class,
            VertexTest.class,
            CoalesceTest.class,


            // sideEffect
            // AddEdgeTest.class,
            AggregateTest.class,
            GroupTest.class,
            GroupCountTest.class,
            InjectTest.class,
            ProfileTest.class,
            SackTest.class,
            SideEffectCapTest.class,
            // SideEffectTest.class,
            StoreTest.class,
            // SubgraphTest.class,
            TreeTest.class,


            // algorithms
            PageRankVertexProgramTest.class,

            // strategy
            ComputerDataStrategyTest.class,
            TraversalVerificationStrategyTest.class
    };

    /**
     * This list of tests in the suite that will be executed.  Gremlin developers should add to this list
     * as needed to enforce tests upon implementations.
     */
    private static final Class<?>[] testsToExecute;

    static {
        final String override = System.getenv().getOrDefault("gremlin.tests", "");
        if (override.equals(""))
            testsToExecute = allTests;
        else {
            final List<String> filters = Arrays.asList(override.split(","));
            final List<Class<?>> allowed = Stream.of(allTests)
                    .filter(c -> filters.contains(c.getName()))
                    .collect(Collectors.toList());
            testsToExecute = allowed.toArray(new Class<?>[allowed.size()]);
        }
    }

    public ProcessComputerSuite(final Class<?> klass, final RunnerBuilder builder) throws InitializationError {
        super(klass, builder, testsToExecute, testsToEnforce, false, TraversalEngine.Type.COMPUTER);
    }

    public ProcessComputerSuite(final Class<?> klass, final RunnerBuilder builder, final Class<?>[] testsToExecute, final Class<?>[] testsToEnforce) throws InitializationError {
        super(klass, builder, testsToExecute, testsToEnforce, false, TraversalEngine.Type.COMPUTER);
    }

    public ProcessComputerSuite(final Class<?> klass, final RunnerBuilder builder, final Class<?>[] testsToExecute, final Class<?>[] testsToEnforce, final boolean gremlinFlavorSuite) throws InitializationError {
        super(klass, builder, testsToExecute, testsToEnforce, gremlinFlavorSuite, TraversalEngine.Type.COMPUTER);
    }

    @Override
    public boolean beforeTestExecution(final Class<? extends AbstractGremlinTest> testClass) {
        final UseEngine[] useEngines = testClass.getAnnotationsByType(UseEngine.class);
        if (null == useEngines || !Stream.of(useEngines).anyMatch(useEngine -> useEngine.value().equals(TraversalEngine.Type.COMPUTER)))
            throw new RuntimeException(String.format("The %s expects all tests to be annotated with @UseEngine(%s) - check %s",
                    ProcessComputerSuite.class.getName(), TraversalEngine.Type.COMPUTER, testClass.getName()));

        return super.beforeTestExecution(testClass);
    }
}
