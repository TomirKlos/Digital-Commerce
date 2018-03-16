var AddProductHandler = function(http) {
    return {
        addProduct: function(id, name) {
            var url = '/add-to-basket/__id__'.replace("__id__", id);
            axios.post(url)
              .then(function (response) {
                document.dispatchEvent(new Event('item_placed_in_basket'));
                  var resultMessage = response.data;
                  if(resultMessage.indexOf("OK") !== -1){
                      iziToast.success({
                          message: 'Dodoano do koszyka '+name,
                          position: 'topRight'
                      });
                      refreshBasket();
                  }else{
                      iziToast.error({
                          message: 'Brak produktu: '+name,
                          position: 'topRight'
                      });
                  }
              })
              .catch(function (error) {
                console.log("it is not possible to add product, check error message");
              });
        }
    }
}