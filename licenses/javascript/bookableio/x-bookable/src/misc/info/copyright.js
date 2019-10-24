const year = 1900 + new Date().getYear();

export default ['$timeout', function($timeout) {
  return {
    restrict: 'A',
    compile() {
      return (scope, element) => {
        const root = scope.$root;

        const refresh = () => {
          const business = scope.business;
          if( !business ) return;

          const info = business.info || {};
          element.html(info.copyright || '&copy;' + year + ' <span>' + (info.registered || business.name) + '</span>');
        };

        root.$on('bookableloaded', () => {
          $timeout(refresh, 0);
        });
        $timeout(refresh, 0);
      };
    }
  };
}];
