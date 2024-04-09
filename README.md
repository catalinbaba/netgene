<h2>Genetic Algorithm and Neural Network Library</h2>

<p>Welcome to our Genetic Algorithm and Neural Network Library for Java! This robust library is designed to offer an integrated solution for simulations that leverage both genetic algorithms (GAs) and neural networks (NNs).</p>

<h3>Features</h3>

<ul>
    <li><strong>Gene Classes</strong>: At the core of our genetic algorithm implementation, the Gene classes represent individual genes. These classes are flexible and can be customized to represent various genetic traits within the context of your specific problem.</li>
    <li><strong>Chromosome Representation</strong>: Chromosomes, composed of multiple genes, are crucial in representing potential solutions. Each Chromosome object in our library can be manipulated and evaluated, acting as a fundamental unit of genetic structure.</li>
    <li><strong>Individual Entities</strong>: Each Chromosome can be part of an Individual, which represents a single entity in the population. Individuals undergo evaluation, selection, and breeding to evolve the population towards optimal solutions.</li>
</ul>

<h2>Integrated Features</h2>

<p>This library offers a comprehensive suite of genetic operators and selection mechanisms, designed to enhance the flexibility and effectiveness of your evolutionary algorithms.</p>

<h3>Crossover Operators</h3>
<ul>
    <li><strong>FixedPointCrossover:</strong> Performs crossover operations at fixed points within the genome, ensuring structured recombination.</li>
    <li><strong>HalfPointCrossover:</strong> A variant of one-point crossover that specifically targets the midpoint of the chromosome.</li>
    <li><strong>OnePointCrossover:</strong> A basic crossover technique where a single crossover point is randomly selected.</li>
    <li><strong>Order1Crossover:</strong> Preserves a relative order of genes, ideal for problems where gene positioning is crucial.</li>
    <li><strong>TwoPointCrossover:</strong> Introduces two points of crossover, allowing for more complex gene mixing.</li>
    <li><strong>UniformCrossover:</strong> Each gene has an equal probability of coming from either parent, maximizing diversity.</li>
</ul>

<h3>Mutators</h3>
<ul>
    <li><strong>BitFlipMutator:</strong> Flips bits at random within a chromosome, mimicking point mutations.</li>
    <li><strong>GaussianMutator:</strong> Applies Gaussian noise to gene values, useful for continuous optimization problems.</li>
    <li><strong>IntegerMutator:</strong> Randomly alters integer genes to explore new phenotypic spaces.</li>
    <li><strong>InversionMutator:</strong> Reverses the sequence of genes between two randomly chosen points to preserve gene linkage.</li>
    <li><strong>RandomMutator:</strong> Introduces random mutations across the chromosome to prevent local minima stagnation.</li>
    <li><strong>ScrambleMutator:</strong> Randomizes the order of genes within a segment of the chromosome, promoting diversity.</li>
    <li><strong>SwapMutator:</strong> Swaps two genes within the chromosome, often used in routing and scheduling problems.</li>
</ul>

<h3>Selection Operators</h3>
<ul>
    <li><strong>CompetitionSelector:</strong> Mimics natural selection by having individuals compete directly for survival.</li>
    <li><strong>RandomSelector:</strong> Chooses individuals randomly, providing a baseline evolutionary pressure.</li>
    <li><strong>RankSelector:</strong> Ranks individuals by fitness and selects according to their rank, reducing selection pressure on top performers.</li>
    <li><strong>RouletteSelector:</strong> Also known as fitness proportionate selection, individuals are selected based on their relative fitness.</li>
    <li><strong>TournamentSelector:</strong> Selects the best individual from randomly selected groups, favoring stronger candidates.</li>
</ul>

<h3>Additional Features</h3>
<p>The library also includes a <strong>GenerationTracker</strong> to monitor and customize data displayed during evolutionary runs. It supports <strong>stop conditions</strong> such as achieving a target fitness or reaching a maximum number of generations. Custom stop conditions can also be defined to further tailor the evolutionary process.</p>

<p>Users can configure custom operators for mutation, crossover, and parent selection to suit specific needs. The library is capable of executing the evolutionary algorithm in parallel, leveraging multicore architectures to accelerate computations.</p>

<p>Regarding genetic representation, the library supports <strong>BitGene</strong>, <strong>IntegerGene</strong>, and <strong>DoubleGene</strong> types, allowing for versatile problem formulations.</p>

<h2>Neural Network Capabilities</h2>

<p>This library allows for the construction of multilayer neural networks, where users can dynamically add neurons to each layer and create multiple layers to suit complex architectures.</p>

<h3>Input Functions</h3>
<ul>
    <li><strong>Sum:</strong> The most common input function, aggregates the weighted inputs from previous neurons.</li>
    <li><strong>Min:</strong> Takes the minimum of the inputs, useful for certain types of decision processes.</li>
    <li><strong>Max:</strong> Takes the maximum of the inputs, useful for neural networks focusing on peak signals.</li>
</ul>

<h3>Activation Functions</h3>
<ul>
    <li><strong>Elu (Exponential Linear Unit):</strong> Helps reduce the vanishing gradient problem for networks that are deep.</li>
    <li><strong>HardSigmoid:</strong> A faster, approximate version of the Sigmoid that is computationally less expensive.</li>
    <li><strong>Linear:</strong> No transformation is applied at all, a simple identity function with a range of negative infinity to infinity.</li>
    <li><strong>Log:</strong> Logarithmic activation function, typically used to scale down large activations.</li>
    <li><strong>Relu (Rectified Linear Unit):</strong> Provides a range from zero to infinity, promoting faster and effective training of deep neural architectures.</li>
    <li><strong>Sigmoid:</strong> Smoothly thresholds the inputs to values between 0 and 1, commonly used in binary classification tasks.</li>
    <li><strong>Sin:</strong> Applies the sine function, which can model periodic behaviors.</li>
    <li><strong>Tanh:</strong> Outputs values between -1 and 1, offering a scaled sigmoid curve.</li>
</ul>

<h3>Learning Algorithms</h3>
<ul>
    <li><strong>AMSGrad, AdaGrad, AdaMax, Adaleta, Adam:</strong> Variants of adaptive learning rate methods, which adjust the learning rate based on past gradients.</li>
    <li><strong>MSGD (Modified Stochastic Gradient Descent), MillerSGD:</strong> Enhancements on traditional SGD that improve convergence rates.</li>
    <li><strong>NAG (Nesterov Accelerated Gradient), Nadam:</strong> Incorporate momentum for faster convergence.</li>
    <li><strong>RMSprop:</strong> An adaptive learning rate method that smooths out the gradients to avoid the exploding gradient problem.</li>
    <li><strong>PRPOPm, RPROPp, SGD (Stochastic Gradient Descent), iRPROPm, iRPROPp:</strong> These methods vary the update rules and adaptation strategies to optimize training under different conditions.</li>
</ul>

<p>The library is standalone and does not require any third-party dependencies, making it easy to integrate and use in various environments without additional overhead.</p>


<p>This library is perfect for anyone looking to explore the potential of genetic algorithms combined with the adaptive capabilities of neural networks in Java. Whether for academic research, simulation modeling, or complex optimization problems, our library provides a foundational structure to build upon.</p>

<h2>Documentation</h2>

<p>The library is fully documented (<a href="https://github.com/catalinbaba/netgene/tree/master/dist/javadoc" target="_blank">JavaDoc</a>).</p>

<h2>Getting Started</h2>

<p>Jenetics requires at least Java 16 to compile and run.</p>

<p>Check out the master branch from GitHub:</p>

<pre>
$ git clone <a href="https://github.com/catalinbaba/netgene.git">https://github.com/catalinbaba/netgene</a> &lt;builddir&gt;
</pre>

<p>Happy coding!</p>
