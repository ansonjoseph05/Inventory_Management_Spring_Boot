package com.inventory_management.Inventory.Management.serviceImpl;

import com.inventory_management.Inventory.Management.dto.InvoiceStocksDTO;
import com.inventory_management.Inventory.Management.entity.Invoice;
import com.inventory_management.Inventory.Management.entity.Message;
import com.inventory_management.Inventory.Management.entity.Stock;
import com.inventory_management.Inventory.Management.error.NotFoundException;
import com.inventory_management.Inventory.Management.repository.InvoiceRepository;
import com.inventory_management.Inventory.Management.repository.StockRepository;
import com.inventory_management.Inventory.Management.service.InvoiceService;
import com.inventory_management.Inventory.Management.utilities.BillInvoiceEmail;
import com.inventory_management.Inventory.Management.utilities.QuantityLowEmailAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private QuantityLowEmailAlert quantityLowEmailAlert;

    @Autowired
    private BillInvoiceEmail billInvoiceEmail;

    @Override
    public Message saveInvoice(Invoice invoice, Long stockId) throws NotFoundException {
        if (!stockRepository.existsById(stockId)){
            throw new NotFoundException("Product with this ID does not exist");
        }

        Stock stock = stockRepository.findById(stockId).get();

        Long stocksId = stock.getStockId();
        String productName = stock.getProduct().getProductName();
        String productCategory = stock.getProduct().getCategory().getCategoryName();
        Long productPrice = stock.getProduct().getProductSellingPrice().getSellingPrice();

        Long stockQty = stock.getStockQuantity();
        Long sellingQty = invoice.getSellingQuantity();

        if ( sellingQty > stockQty ){

            Message message=new Message();
            message.setMessage(" Stock quantity is less than Selling Quantity");
            return message;

        }

        invoice.setStockId(stocksId);
        invoice.setProductName(productName);
        invoice.setCategoryName(productCategory);
        invoice.setProductPrice(productPrice);

        invoiceRepository.save(invoice);
        stockQty = stockQty - sellingQty;
        stock.setStockQuantity(stockQty);
        stockRepository.save(stock);



        if (stockQty<50){
            quantityLowEmailAlert.sendOrderSuccessfulEmail(
                    "anson.joseph05@gmail.com",
                    "Stock with Id "+stock.getStockId()+" is low below 50 units",
                    "Alert"
            );
        }

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());


        billInvoiceEmail.sendBillInvoiceEmail(""+invoice.getCustomerEmail(),
                "Dear " +invoice.getCustomerName()+ ",\n"+ "The details of your purchase from Company_Name on "+currentDateTime+" are \n"+
                "Product Name : " +invoice.getProductName()+ "\n" +
                "Product Price : " +invoice.getProductPrice()+ "\n" +
                "Product Quantity : " +invoice.getSellingQuantity(),
                "Bill Invoice"
        );


        Message message=new Message();
        message.setMessage("Invoice Generated");
        return message;
    }

    @Override
    public List<InvoiceStocksDTO> fetchByInvoiceId(Long invoiceId) throws NotFoundException {
        if (!invoiceRepository.existsById(invoiceId)){
            throw new NotFoundException("Invoice with this id does not exist");
        }
        return invoiceRepository.findById(invoiceId)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceStocksDTO> fetchAllInvoice() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Message updateInvoice(Long invoiceId, Invoice invoice) throws NotFoundException {
        Invoice invoiceDB = invoiceRepository.findById(invoiceId).get();

        if (!invoiceRepository.existsById(invoiceId)){
            throw new NotFoundException("Invoice with this id does not exist");
        }

        if(Objects.nonNull(invoice.getSellingQuantity())&&
                !"".equalsIgnoreCase(String.valueOf(invoice.getSellingQuantity()))){
            invoiceDB.setSellingQuantity(invoice.getSellingQuantity());
        }
        invoiceRepository.save(invoiceDB);
        Message message=new Message();
        message.setMessage("successfully updated");
        return message;
    }

    @Override
    public Message deleteInvoice(Long invoiceId) throws NotFoundException {
        if (!invoiceRepository.existsById(invoiceId)){
            throw new NotFoundException("Invoice with this id does not exist");
        }
        invoiceRepository.deleteById(invoiceId);
        Message message=new Message();
        message.setMessage("deleted successfully");
        return message;
    }

    @Override
    public List<InvoiceStocksDTO> getByInvoiceId(Long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private InvoiceStocksDTO convertEntityToDto(Invoice invoice) {
        InvoiceStocksDTO invoiceStocksDTO =
                new InvoiceStocksDTO();


        invoiceStocksDTO.setInvoiceId(invoice.getInvoiceId());
        invoiceStocksDTO.setProductName(invoice.getProductName());
        invoiceStocksDTO.setCategoryName(invoice.getCategoryName());
        invoiceStocksDTO.setProductPrice(invoice.getProductPrice());
        invoiceStocksDTO.setSellingQuantity(invoice.getSellingQuantity());
        invoiceStocksDTO.setCustomerEmail(invoice.getCustomerEmail());
        invoiceStocksDTO.setCustomerName(invoice.getCustomerName());



        return invoiceStocksDTO;
    }
}
