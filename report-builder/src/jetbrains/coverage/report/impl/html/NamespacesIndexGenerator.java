package jetbrains.coverage.report.impl.html;

import jetbrains.coverage.report.CoverageStatistics;
import jetbrains.coverage.report.StatisticsCalculator;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

/**
* @author Eugene Petrenko (eugene.petrenko@jetbrains.com)
*         14.10.10 12:59
*/
public class NamespacesIndexGenerator extends BaseGenerator {
  public NamespacesIndexGenerator(@NotNull TemplateProcessor templateFactory, @NotNull LocalGeneratorPaths paths) {
    super(templateFactory, paths);
  }

  public void generateNamespacesIndex(ModuleInfo module, Collection<String> namespaces, final StatisticsCalculator covStatsCalculator) throws IOException {
    for (SortOption sortOption: getGenerateSortOptions(covStatsCalculator)) {
      Map<String, Object> templateModel = new HashMap<String, Object>();
      templateModel.put("module", module);
      templateModel.put("namespaces", prepareNamespaces(module, namespaces, covStatsCalculator, sortOption));
      templateModel.put("statsCalculator", covStatsCalculator);
      templateModel.put("sortOption", sortOption);

      myTemplateFactory.renderTemplate(templateModel, myPaths.getNamespacesIndexPath(module, sortOption));
    }
  }

  private List<String> prepareNamespaces(final ModuleInfo module,
                                         final Collection<String> namespaces,
                                         final StatisticsCalculator covStatsCalculator,
                                         final SortOption sortOption) {

    List<String> result = removeNamespacesWithoutStatistics(module, namespaces, covStatsCalculator);
    Collections.sort(result, sortOption.createNamespaceComparator(module, covStatsCalculator));
    return result;
  }

  private List<String> removeNamespacesWithoutStatistics(@NotNull final ModuleInfo module,
                                                               @NotNull final Collection<String> namespaces,
                                                               @NotNull final StatisticsCalculator covStatsCalculator) {
    return filterCovered(namespaces, new Converter<String>() {
      @NotNull
      public CoverageStatistics convert(String s) {
        return covStatsCalculator.getForNamespace(module.getName(), s);
      }
    });
  }
}
